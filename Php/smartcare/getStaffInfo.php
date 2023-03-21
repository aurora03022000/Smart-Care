<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);

    $sql = "SELECT * FROM user WHERE user_id='$currentUserID' and isExist=1 ORDER BY staff_name ASC";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){
            echo $row['username']."`".$row['password']."`".$row['staff_name']."`".$row['birthdate']."`".$row['gender']."`".$row['address']."`".$row['number']."`".$row['profile']."%";
        }
    }

    else{
        echo 'failure';
    }

}
?>