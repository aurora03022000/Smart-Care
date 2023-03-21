<?php
if(isset($_POST['username'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $username = validate($_POST['username']);

    $sql = "SELECT * FROM user WHERE username='$username' and isExist=1";
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        echo 'success';
    }

    else{
        echo 'failure';
    }

}
?>