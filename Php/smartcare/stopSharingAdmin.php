<?php
if(isset($_POST['patient_id'])){
    require_once "conn.php";
    require_once "validate.php";
        
    $patientID = validate($_POST['patient_id']);

    $getPreviousShareID = "SELECT * FROM patient WHERE patient_id = '$patientID'";
    $getPreviousShareIDResult = mysqli_query($conn, $getPreviousShareID);

    if($getPreviousShareIDResult){
        while($row1 = mysqli_fetch_assoc($getPreviousShareIDResult)){
            $getStaffID = "SELECT * FROM user WHERE user_id = '$row1[share_id]'";
            $getStaffIDResult = mysqli_query($conn, $getStaffID);

            if($getStaffIDResult){
                while($row2 = mysqli_fetch_assoc($getStaffIDResult)){
                    $stopSharingPatient = "UPDATE patient SET share_id = '' WHERE patient_id = '$patientID'";
                    $stopSharingPatientResult = mysqli_query($conn, $stopSharingPatient);

                    if($stopSharingPatientResult){
                        $staffName = $row2['staff_name'];
                            $activity = "Stopped patient sharing to medical staff named ".$staffName;
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