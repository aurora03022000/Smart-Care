<?php
    require_once "conn.php";
    require_once "validate.php";
    
    
    $sql = "SELECT * FROM current_patient_monitored";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM patient_health_log WHERE patient_id='$row[patient_id]'";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row1=mysqli_fetch_assoc($result1)){
        
                    if($result1){
                        echo $row1['pulse_rate']."`".$row1['oxygen_saturation']."`".$row1['body_temperature']."`".$row1['blood_pressure']."`".$row1['respiratory_rate']."`".$row1['room_temperature']."`".$row1['humidity']."`".$row1['air_quality']."%";
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
    }

    else{
        echo 'failure';
    }

?>