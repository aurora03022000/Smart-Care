<?php
if(isset($_POST['sharedPatientID1']) && isset($_POST['staffID1'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $sharedPatientID1 = validate($_POST['sharedPatientID1']);
    $staffID1 = validate($_POST['staffID1']);

    $sql = "UPDATE patient SET share_id='$staffID1' WHERE patient_id='$sharedPatientID1' and isExist=1";
    $result = $conn->query($sql);
    
    if($result){

        $getPatient = "SELECT * FROM patient WHERE patient_id = '$sharedPatientID1'";
        $getPatientResult = mysqli_query($conn, $getPatient);

        if($getPatientResult){
            while($row = mysqli_fetch_assoc($getPatientResult)){
                $getSharedToStaffName = "SELECT * FROM user WHERE user_id = '$staffID1'";
                $getSharedToStaffNameResult = mysqli_query($conn, $getSharedToStaffName);

                if($getSharedToStaffNameResult){
                    while($row1 = mysqli_fetch_assoc($getSharedToStaffNameResult)){
                        $patientName = $row['patient_name'];
                        $staffName = $row1['staff_name'];
                        $encoder = $row['encoder'];
                        $activity = "Shared a patient named ".$patientName." to ".$staffName;
                        date_default_timezone_set('Asia/Manila');
                        $activityDate = date("F d, Y");
                        $activityDateFinal = new DateTime($activityDate);
                        $newActivityDate = $activityDateFinal->format('F d, Y');
                        $activityTime = date("h:i a");
                        $user = $encoder;
                
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
?>