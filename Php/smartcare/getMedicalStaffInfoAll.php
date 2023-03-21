<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);

    $sql = "SELECT * FROM user WHERE user_id !='$currentUserID' and username != 'admin' and isExist=1 ORDER BY staff_name ASC";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){
            echo $row['staff_name']."`".$row['user_id']."%";
        }
    }

    else{
        echo 'failure';
    }

}
?>