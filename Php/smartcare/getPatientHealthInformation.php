<?php
    if(isset($_POST['patient_id'])){
        require_once "conn.php";
        require_once "validate.php";
            
        $patientID = validate($_POST['patient_id']);

        $getPatientName = "SELECT * FROM patient WHERE patient_id = '$patientID'";
        $getPatientNameResult = $conn->query($getPatientName);

        if($getPatientNameResult){
            while($row1 = mysqli_fetch_assoc($getPatientNameResult)){
                $getPatientHealthLog = "SELECT * FROM patient_health_log WHERE patient_id='$row1[patient_id]'";
                $getPatientHealthLogResult = mysqli_query($conn, $getPatientHealthLog);

                if($getPatientHealthLogResult){
                    while($row2 = mysqli_fetch_assoc($getPatientHealthLogResult)){
                        echo $row1['patient_name'].'`'.$row2['pulse_rate'].'`'.$row2['oxygen_saturation'].'`'.$row2['body_temperature'].'`'.$row2['blood_pressure'].'`'.$row2['respiratory_rate'].'`'.$row2['room_temperature'].'`'.$row2['humidity'].'`'.$row2['air_quality'];
                    }
                }

                else{
                    echo 'failure';
                }
            }
        }

        else{
            echo 'failure';
        }
}    
?>