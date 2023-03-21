<?php
if(isset($_POST['patient_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patient_id = validate($_POST['patient_id']);
    
    date_default_timezone_set('Asia/Manila');
    $dateDeleted = date("F d, Y");
    $dateDeletedFinal = new DateTime($dateDeleted);
    $newDateDeleted = $dateDeletedFinal->format('F d, Y');

        $sql1 = "UPDATE patient SET isExist=0, date_dismiss = '$newDateDeleted'  WHERE patient_id='$patient_id'";
        $result1 = $conn->query($sql1);

        if($result1){
            $sql2 = "UPDATE patient_health_log SET isExist=0 WHERE patient_id='$patient_id'";
            $result2 = $conn->query($sql2);

            if($result2){ 
                
                $sql3 = "SELECT * FROM patient WHERE patient_id='$patient_id'";
                $result3 = $conn->query($sql3);

                if($result3){
                    while($row = mysqli_fetch_assoc($result3)){
                        $sql4 = "UPDATE deviceid SET device_status='Available' WHERE device_id='$row[deviceID]'";
                        $result4 = $conn->query($sql4);

                        if($result4){
                            $sql5 = "UPDATE room SET room_status='Available' WHERE room_number='$row[room]'";
                            $result5 = $conn->query($sql5);

                            if($result5){
                                $patientName = $row['patient_name'];
                                $activity = "Dismissed patient named ".$patientName;
                                date_default_timezone_set('Asia/Manila');
                                $activityDate = date("F d, Y");
                                $activityDateFinal = new DateTime($activityDate);
                                $newActivityDate = $activityDateFinal->format('F d, Y');
                                $activityTime = date("h:i a");
                                $user = 0;

                                $getAdminId = "SELECT * FROM user WHERE username='admin'";
                                $getAdminIdResult = mysqli_query($conn, $getAdminId);

                                if($getAdminIdResult){
                                    while($rowLast = mysqli_fetch_assoc($getAdminIdResult)){
                                        $user =  $rowLast['user_id'];
                    
                                        $addHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                                        $addHistoryResult = mysqli_query($conn, $addHistory);

                                        if($addHistoryResult){
                                            echo 'success';
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

            else{
                echo 'failure'; 
            }
        }

        else{
            echo 'failure';
        }

}
?>