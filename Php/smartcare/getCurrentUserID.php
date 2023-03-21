<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM current_user_id";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){
            echo $row['id'];
        }
    }

    else{
        echo 'failure';
    }

?>