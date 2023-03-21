<?php
    if(isset($_POST['user_id'])){
        require_once "conn.php";
        require_once "validate.php";
            
        $userID = validate($_POST['user_id']);

        $sql2 = "SELECT * FROM user WHERE user_id = '$userID'";
        $result2 = $conn->query($sql2);

        if($result2){
            while($row1 = mysqli_fetch_assoc($result2)){
                echo $row1['date_registered']."`". $row1['date_deleted']."`".$row1['user_id'].'`'.$row1['username'].'`'.$row1['password'].'`'.$row1['staff_name'].'`'.$row1['gender'].'`'.$row1['birthdate'].'`'.$row1['address'].'`'.$row1['number'];
            }
        }

        else{
            echo 'failure';
        }
    }
?>