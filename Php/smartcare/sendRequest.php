<?php
if(isset($_POST['currentUserID']) && isset($_POST['message'])){

    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);
    $message = validate($_POST['message']);

    $sql1 = "INSERT INTO request(user_id, message) VALUES('$currentUserID', '$message')";
    $result1 = $conn->query($sql1);

   if($result1){
        $activity = "Sent feedback";
        date_default_timezone_set('Asia/Manila');
        $activityDate = date("d-m-y");
        $activityDateFinal = new DateTime($activityDate);
        $newActivityDate = $activityDateFinal->format('F d, Y');
        $activityTime = date("h:ia");
        $user = $currentUserID;

        $insertHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
        $insertHistoryResult = mysqli_query($conn, $insertHistory);

        if($insertHistoryResult){
            echo "success";
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