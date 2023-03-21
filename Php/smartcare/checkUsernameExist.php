<?php
if(isset($_POST['username'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $username = validate($_POST['username']);

        $sql1 = "SELECT * FROM user WHERE username = '$username' AND isExist=1";
        $result1 = $conn->query($sql1);
        $countRows = mysqli_num_rows($result1);

        if($result1){
            if($countRows > 0){
                echo 'exist';
            }

            else{
                echo 'success';
            }
        }

        else{
            echo 'failure';
        }

}
?>