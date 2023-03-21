<?php
if(isset($_POST['passwordInput']) && isset($_POST['nameInput']) && isset($_POST['birthdateInput']) && isset($_POST['genderInput']) && isset($_POST['addressInput']) && isset($_POST['numberInput']) && isset($_POST['currentUserID']) && isset($_POST['profileInput'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);
    $password = validate($_POST['passwordInput']);
    $name = validate($_POST['nameInput']);
    $birthdate = validate($_POST['birthdateInput']);
    $gender = validate($_POST['genderInput']);
    $address = validate($_POST['addressInput']);
    $number = validate($_POST['numberInput']);
    $profile = validate($_POST['profileInput']);

    if($profile == ""){
        $sql = "UPDATE user SET password='$password', number='$number', staff_name='$name', birthdate='$birthdate', gender='$gender', address='$address' WHERE user_id='$currentUserID' and isExist=1";
        $result = $conn->query($sql); 
    }

    else{
        $sql = "UPDATE user SET password='$password', number='$number', staff_name='$name', birthdate='$birthdate', gender='$gender', address='$address', profile='$profile' WHERE user_id='$currentUserID' and isExist=1";
        $result = $conn->query($sql);
    }
    
    if($result){

        $sql1 = "SELECT * FROM user WHERE user_id='$currentUserID' and isExist=1 ORDER BY staff_name ASC";
        $result1 = $conn->query($sql1);

        if($result1){
            $activity = "Updated account information";
            date_default_timezone_set('Asia/Manila');
            $activityDate = date("F d, Y");
            $activityDateFinal = new DateTime($activityDate);
            $newActivityDate = $activityDateFinal->format('F d, Y');
            $activityTime = date("h:i a");
            $user = $currentUserID;
    
            $insertHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
            $insertHistoryResult = mysqli_query($conn, $insertHistory);
    
            if($insertHistoryResult){
                while($row = mysqli_fetch_assoc($result1)){
                    echo $row['username']."`".$row['password']."`".$row['staff_name']."`".$row['birthdate']."`".$row['gender']."`".$row['address']."`".$row['number']."`".$row['profile']."%";
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

    else{
        echo 'failure';
    }

}
?>