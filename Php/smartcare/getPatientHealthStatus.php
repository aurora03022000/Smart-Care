<?php
if(isset($_POST['patientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID = validate($_POST['patientID']);
    
    $sql = "SELECT * FROM patient_health_log WHERE patient_id='$patientID' and isExist=1 ";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['pulse_rate']."`".$row['oxygen_saturation']."`".$row['body_temperature']."`".$row['blood_pressure']."`".$row['respiratory_rate']."`".$row['room_temperature']."`".$row['humidity']."`".$row['air_quality']."%";
        }
    }

    else{
        echo 'failure';
    }

}
?>