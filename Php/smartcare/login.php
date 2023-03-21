<?php
if(isset($_POST['username']) && isset($_POST['password'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $username = validate($_POST['username']);
    $password = validate($_POST['password']);

    $sql = "SELECT * FROM user WHERE username='$username' and password='$password' and isExist=1";
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        while($row=mysqli_fetch_assoc($result)){
            if($row['username'] == "admin"){
                echo "failure";
            }

            else{
                $activity = "Logged in";
                date_default_timezone_set('Asia/Manila');
                $activityDate = date("F d, Y");
                $activityDateFinal = new DateTime($activityDate);
                $newActivityDate = $activityDateFinal->format('F d, Y');
                $activityTime = date("h:i a");
                $user = $row['user_id'];

                $insertHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                $insertHistoryResult = mysqli_query($conn, $insertHistory);

                if($insertHistoryResult){
                    echo $row['user_id'];
                }

                else{
                    echo "failure";
                }
            }
            // $sql1 = "UPDATE current_user_id SET id='$row[user_id]'";
            // $result1 = $conn->query($sql1);

            // if($result1){
                
                
            // }

            // else{
            //     echo 'failure';
            // }
        }
        
    }

    else{
        echo 'failure';
    }

}
?>