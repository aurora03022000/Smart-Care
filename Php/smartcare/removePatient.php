<?php
if(isset($_POST['patientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID = validate($_POST['patientID']);
    
        $sql1 = "SELECT * FROM patient WHERE patient_id='$patientID' and isExist=1";
        $result1 = $conn->query($sql1);

        if($result1){
            while($row=mysqli_fetch_assoc($result1)){
                $deviceID = $row['deviceID'];
                $room_number = $row['room'];
            }
                
                $sql3="UPDATE deviceid SET device_status='Available' WHERE device_id = '$deviceID'";
                $result3 = $conn->query($sql3);
    
                if($result3){
                    $sql4 = "UPDATE patient_health_log SET isExist=0 WHERE patient_id='$patientID' and isExist=1";
                    $result4 = $conn->query($sql4);

                    if($result4){

                        date_default_timezone_set('Asia/Manila');
                        $dateDismissed = date("F d, Y");
                        $dateDismissedFinal = new DateTime($dateDismissed);
                        $newDateDismissed = $dateDismissedFinal->format('F d, Y');

                        $sql5 = "UPDATE patient SET isExist=0, date_dismiss='$newDateDismissed' WHERE patient_id='$patientID' and isExist=1";
                        $result5 = $conn->query($sql5);

                        if($result4){

                            $sql6 = "DELETE FROM patient_logs WHERE patient_id='$patientID'";
                            $sql6Result = mysqli_query($conn, $sql6);

                            if($sql6){
                                $selectPatient = "SELECT * FROM patient WHERE patient_id = '$patientID'";
                                $selectPatientResult = mysqli_query($conn, $selectPatient);

                                if($selectPatientResult){
                                    while($row1 = mysqli_fetch_assoc($selectPatientResult)){
                                        if($row1['share_id'] == "" || $row1['share_id'] == " "){
                                            $patientName = $row1['patient_name'];
                                            $activity = "Dismissed patient named ".$patientName;
                                            date_default_timezone_set('Asia/Manila');
                                            $activityDate = date("F d, Y");
                                            $activityDateFinal = new DateTime($activityDate);
                                            $newActivityDate = $activityDateFinal->format('F d, Y');
                                            $activityTime = date("h:i a");
                                            $user = $row1['encoder'];
    
                                            $addHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                                            $addHistoryResult = mysqli_query($conn, $addHistory);
    
                                            if($addHistoryResult){
                                                echo 'success';
                                            }
    
                                            else{
                                                echo 'failure';
                                            }
                                        }

                                        else{
                                            $selectUserShared = "SELECT * FROM user WHERE user_id = $row1[share_id]";
                                            $selectUserSharedResult = mysqli_query($conn, $selectUserShared);

                                            if($selectUserSharedResult){
                                                $patientName = $row1['patient_name'];
                                                $activity = "Dismissed patient named ".$patientName;
                                                date_default_timezone_set('Asia/Manila');
                                                $activityDate = date("F d, Y");
                                                $activityDateFinal = new DateTime($activityDate);
                                                $newActivityDate = $activityDateFinal->format('F d, Y');
                                                $activityTime = date("h:i a");
                                                $user = $row1['user_id'];
    
                                                $addHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                                                $addHistoryResult = mysqli_query($conn, $addHistory);
    
                                                if($addHistoryResult){
                                                    echo 'success';
                                                }
    
                                                else{
                                                    echo 'failure';
                                                }
                                            }

                                            else{

                                            }
                                            
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