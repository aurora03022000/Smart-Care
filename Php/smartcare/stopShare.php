<?php
if(isset($_POST['patientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID = validate($_POST['patientID']);

    $sql = "UPDATE patient SET share_id='' WHERE patient_id = '$patientID' and isExist=1";
    $result = $conn->query($sql);
    
    if($result){
        $getPatient = "SELECT * FROM patient WHERE patient_id = '$patientID'";
        $getPatientResult = mysqli_query($conn, $getPatient);

        if($getPatientResult){
            while($row = mysqli_fetch_assoc($getPatientResult)){
                $encoder = $row['encoder'];

                $getUser = "SELECT * FROM user WHERE user_id = '$encoder'";
                $getUserResult = mysqli_query($conn, $getUser);

                if($getUserResult){
                    while($row1 = mysqli_fetch_assoc($getUserResult)){
                        $staffName = $row1['staff_name'];
                        $encoder = $row['encoder'];
                        $activity = "Stopped patient sharing to medical staff named ".$staffName;
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
                    echo "failure";
                }
            }
        }
    }

    else{
        echo 'failure';
    }

}
?>