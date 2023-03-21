<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM current_patient_monitored WHERE id='1'";
    $result = $conn->query($sql);
    
    if($result){
        while($row=mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM patient WHERE patient_id='$row[patient_id]' and isExist=1";
            $result1 = $conn->query($sql1);

            if($result1){
                while($row1=mysqli_fetch_assoc($result1)){
                    $sql2 = "SELECT * FROM deviceid WHERE device_id='$row1[deviceID]' ORDER BY device_id ASC";
                    $result2 = $conn->query($sql2);

                    if($result2){
                        while($row2=mysqli_fetch_assoc($result2)){
                            echo $row2['ipAddress'];
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
    }

    else{
        echo 'failure';
    }

?>