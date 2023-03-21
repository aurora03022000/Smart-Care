<?php
if(isset($_POST['patient_id']) && isset($_POST['share_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID = validate($_POST['patient_id']);
    $share_id = validate($_POST['share_id']);
    
    $getStaffID = "SELECT * FROM user WHERE staff_name = '$share_id' and isExist = 1";
    $getStaffIDResult = mysqli_query($conn, $getStaffID);

    if($getStaffIDResult){
        while($row0 = mysqli_fetch_assoc($getStaffIDResult)){
               
            $sql = "UPDATE patient SET share_id='$row0[user_id]' WHERE patient_id = '$patientID' and isExist=1";
            $result = mysqli_query($conn, $sql);
            
            if($result){
                $getPatientSharedTo = "SELECT * FROM patient WHERE patient_id ='$patientID' and isExist=1";
                $getPatientSharedToResult = mysqli_query($conn, $getPatientSharedTo);

                if($getPatientSharedToResult){
                    while($row1 = mysqli_fetch_assoc($getPatientSharedToResult)){
                        $getStaffName = "SELECT * FROM user WHERE user_id = '$row1[share_id]'";
                        $getStaffNameResult = mysqli_query($conn, $getStaffName);

                        if($getStaffNameResult){
                            while($row2 = mysqli_fetch_assoc($getStaffNameResult)){
                                $patientName = $row1['patient_name'];
                                $activity = "Shared a patient named ".$patientName;
                                date_default_timezone_set('Asia/Manila');
                                $activityDate = date("F d, Y");
                                $activityDateFinal = new DateTime($activityDate);
                                $newActivityDate = $activityDateFinal->format('F d, Y');
                                $activityTime = date("h:i a");
                                
                                $getAdminId = "SELECT * FROM user WHERE username='admin'";
                                $getAdminIdResult = mysqli_query($conn, $getAdminId);

                                if($getAdminIdResult){
                                    while($rowLast = mysqli_fetch_assoc($getAdminIdResult)){
                                        $user =  $rowLast['user_id'];

                                        $insertHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                                        $insertHistoryResult = mysqli_query($conn, $insertHistory);
                                
                                        if($insertHistoryResult){
                                            echo $row2['staff_name'];
                                        }
                                
                                        else{
                                            echo "failure";
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