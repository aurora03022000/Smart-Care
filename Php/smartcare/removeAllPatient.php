<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);

    $sql1 = "SELECT * FROM patient WHERE encoder='$currentUserID' or share_id='$currentUserID' and isExist=1";
    $result1 = $conn->query($sql1);

   if($result1){


        while($row=mysqli_fetch_assoc($result1)){
                $patient_id = $row['patient_id'];
                $deviceID = $row['deviceID'];
                $room_number = $row['room'];

                $sql2="UPDATE room SET room_status='Available' WHERE room_number =  '$room_number' and isExist=1";
                $result2 = $conn->query($sql2);
                
                $sql3="UPDATE deviceid SET device_status='Available' WHERE device_id = '$deviceID' and isExist=1";
                $result3 = $conn->query($sql3);
    
                if($result2 && $result3){
                    $sql4 = "DELETE FROM patient_health_log WHERE patient_id='$patient_id' and isExist=1";
                    $result4 = $conn->query($sql4);
                    
                    if($result4){
                        $sql5 = "UPDATE patient SET isExist=0 WHERE patient_id='$patient_id'";
                        $result5 = $conn->query($sql5);

                        if($result5){
                            echo 'success';
                        }

                        else{
                            echo 'failure';
                        }
                    }

                    else{
                        echo 'failure';
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