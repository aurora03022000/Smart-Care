<?php
if(isset($_POST['user_id'])){
    require_once "conn.php";
    require_once "validate.php";
        
    $userID = validate($_POST['user_id']);

    $sql2 = "SELECT * FROM user WHERE user_id = '$userID'";
    $result2 = $conn->query($sql2);

    if($result2){
        while($row1 = mysqli_fetch_assoc($result2)){
            echo $row1['profile'];
        }
    }

    else{
        echo 'failure';
    }
}
    

?>