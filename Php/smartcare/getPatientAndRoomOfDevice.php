<?php
if(isset($_POST['device_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $device_id = validate($_POST['device_id']);
    
        $sql1 = "SELECT * FROM deviceid WHERE device_id='$device_id' ORDER BY device_id ASC";
        $result1 = $conn->query($sql1);
        $count = mysqli_num_rows($result1);

        if($count > 0){
            while($row = mysqli_fetch_assoc($result1)){
                if($row['device_status'] == "Not Available"){
                    $sql2 = "SELECT * FROM patient WHERE deviceID='$row[device_id]' and isExist=1";
                    $result2 = $conn->query($sql2);
                    $count1 = mysqli_num_rows($result2);
                    
                    if($result2){
                        while($row1 = mysqli_fetch_assoc($result2)){
                            echo $row1['deviceID'].'`'.$row['device_status'].'`'.$row['ipAddress'].'`'.$row1['patient_name'].'`'.$row1['room'];
                        }
                    }

                    else{
                        echo 'failure';
                    }
                    
                }

                else{
                    echo $row['device_id'].'`'.$row['device_status'].'`'.$row['ipAddress'].'`'."-".'`'."-";
                }
            }
        }
        
        else if($count == 0){
            echo 'none';
        }

        else{
            echo 'failure';
        }

}
?>