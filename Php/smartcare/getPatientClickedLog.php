<?php

        require_once "conn.php";
        require_once "validate.php";
        
        $sql = "SELECT * FROM current_log_open";
        $result = $conn->query($sql);
        
        if($result){
            while($row = mysqli_fetch_assoc($result)){
                $sql1 = "SELECT * FROM patient_logs WHERE id='$row[log_id]'";
                $result1 = $conn->query($sql1);
                
                if($result1){
                    while($row1 = mysqli_fetch_assoc($result1)){
                        echo $row1['time']."`".$row1['pulse_rate']."`".$row1['oxygen_saturation']."`".$row1['body_temperature']."`".$row1['blood_pressure']."`".$row1['respiratory_rate']."`".$row1['room_temperature']."`".$row1['humidity']."`".$row1['air_quality']."`".$row1['date']."%";
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