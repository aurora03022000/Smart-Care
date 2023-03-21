<?php
if(isset($_POST['username']) && isset($_POST['password']) && isset($_POST['staff_name']) && isset($_POST['staff_birthdate']) && isset($_POST['staff_gender']) && isset($_POST['staff_address']) && isset($_POST['staff_number'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $username = validate($_POST['username']);
    $password = validate($_POST['password']);
    $staff_name = validate($_POST['staff_name']);
    $staff_birthdate = validate($_POST['staff_birthdate']);
    $staff_gender = validate($_POST['staff_gender']);
    $staff_address = validate($_POST['staff_address']);
    $staff_number = validate($_POST['staff_number']);

    date_default_timezone_set('Asia/Manila');
    $date_registered = date("F d, Y");
    $date_registeredFinal = new DateTime($date_registered);
    $newDate_registered = $date_registeredFinal->format('F d, Y');

    $birthdate = new DateTime($staff_birthdate);
    $birthdateFinal = $birthdate->format('F d, Y');

        $sql1 = "INSERT INTO user(username,password,number,staff_name,birthdate,gender,address,date_registered,profile,isExist) VALUES('$username', '$password', '$staff_number', '$staff_name', '$birthdateFinal', '$staff_gender', '$staff_address', '$newDate_registered', 'profile/default.png',1 )";
        $result1 = $conn->query($sql1);

        if($result1){
            $activity = "Registered medical staff named ".$staff_name;
            date_default_timezone_set('Asia/Manila');
            $activityDate = date("F d, Y");
            $activityDateFinal = new DateTime($activityDate);
            $newActivityDate = $activityDateFinal->format('F d, Y');
            $activityTime = date("h:i a");

            $getAdminId = "SELECT * FROM user WHERE username='admin'";
            $getAdminIdResult = mysqli_query($conn, $getAdminId);

            if($getAdminIdResult){
                while($row = mysqli_fetch_assoc($getAdminIdResult)){
                    $user =  $row['user_id'];

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
?>