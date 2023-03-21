<?php
if(isset($_POST['username']) && isset($_POST['password'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $username = validate($_POST['username']);
    $password = validate($_POST['password']);

    $sql = "SELECT * FROM user WHERE username='$username' and password='$password' and isExist=1";
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        while($row = mysqli_fetch_assoc($result)){
            echo $row['user_id'];
        }
    }

    else{
        echo 'failure';
    }

}
?>