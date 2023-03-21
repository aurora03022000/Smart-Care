<?php
if(isset($_POST['patient_id']) && isset($_POST['sharedPatientID1'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patient_id = validate($_POST['patient_id']);
    $user_id = validate($_POST['sharedPatientID1']); // this is a name

    $getUserName = "SELECT * FROM user WHERE staff_name = '$user_id'";
    $getUserNameResult = mysqli_query($conn, $getUserName);

    if($getUserNameResult){
        while($row0 = mysqli_fetch_assoc($getUserNameResult)){
            $user_id = $row0['user_id'];

            $sql = "UPDATE patient SET share_id='$user_id' WHERE patient_id='$patient_id'";
            $result = $conn->query($sql);
            
            if($result){
        
                $getPatient = "SELECT * FROM patient WHERE patient_id = '$patient_id'";
                $getPatientResult = mysqli_query($conn, $getPatient);
        
                if($getPatientResult){
                    while($row1 = mysqli_fetch_assoc($getPatientResult)){
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
                                        echo "success";
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
                    echo "failure"; 
                }
            }
        
            else{
                echo 'failure';
            }
        }
    }

    else{
        echo "failure";
    }
}
?>