<?php

$target_dir = "profile";
$image = $_POST['image'];
$currentUserID = $_POST['currentUserID'];

require_once "conn.php";
require_once "validate.php";

if(!file_exists($target_dir)){
    mkdir($target_dir, 077, true);
}

$target_dir = $target_dir ."/".rand() . "_". time() . ".jpeg";

if(file_put_contents($target_dir, base64_decode($image))){

    $sql = "UPDATE user SET profile='$target_dir' WHERE user_id = '$currentUserID' and isExist=1";
    $result = mysqli_query($conn, $sql);

    if($result){
        $sql1 = "SELECT * FROM user WHERE user_id='$currentUserID' and isExist=1";
        $result1 = $conn->query($sql1);
        
        if($result1){
            while($row = mysqli_fetch_assoc($result1)){
                echo $row['profile'];
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

?>