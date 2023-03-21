<?php
if(isset($_POST['user_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $user_id = validate($_POST['user_id']);
    
    date_default_timezone_set('Asia/Manila');
    $dateDeleted = date("F d, Y");
    $dateDeletedFinal = new DateTime($dateDeleted);
    $newDateDeleted = $dateDeletedFinal->format('F d, Y');

    $checkIfUsed = "SELECT * FROM patient WHERE encoder = '$user_id' and isExist = 1";
    $checkIfUsedResult = mysqli_query($conn, $checkIfUsed);
    $checkIfUsedCount = mysqli_num_rows($checkIfUsedResult);

    if($checkIfUsedCount > 0){
        echo 'used';
    }

    else if($checkIfUsedCount == 0){
        $sql1 = "UPDATE user SET isExist=0, date_deleted = '$newDateDeleted' WHERE user_id='$user_id'";
        $result1 = $conn->query($sql1);

        if($result1){
            $getStaffName = "SELECT * FROM user WHERE user_id = '$user_id'";
            $getStaffNameResult = mysqli_query($conn, $getStaffName);

            if($getStaffNameResult){
                while($row = mysqli_fetch_assoc($getStaffNameResult)){
                    $staffName = $row['staff_name'];
                    $activity = "Deleted Staff named ".$staffName;
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