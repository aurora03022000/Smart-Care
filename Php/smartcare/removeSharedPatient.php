<?php
if(isset($_POST['patientID2'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID2 = validate($_POST['patientID2']);
    
        $sql1 = "SELECT * FROM patient WHERE patient_id='$patientID2' and isExist=1";
        $result1 = $conn->query($sql1);

        if($result1){
            while($row=mysqli_fetch_assoc($result1)){
                $deviceID = $row['deviceID'];
                $room_number = $row['room'];
                $shared_id = $row['share_id'];
                $patientName = $row['patient_name'];
            }

                $sql3="UPDATE deviceid SET device_status='Available' WHERE device_id = '$deviceID'";
                $result3 = $conn->query($sql3);
    
                if($result3){
                    $sql4 = "UPDATE patient SET isExist=0 WHERE patient_id='$patientID2'";
                    $result4 = $conn->query($sql4);

                    if($result4){
                        $getEncoder = "SELECT * FROM user WHERE user_id = '$shared_id'";
                        $getEncoderResult = mysqli_query($conn, $getEncoder);

                        if($getEncoderResult){
                            while($row1 = mysqli_fetch_assoc($getEncoderResult)){
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
?>