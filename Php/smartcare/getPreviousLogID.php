<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM  previous_logs";
    $result = $conn->query($sql);
    
    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['previous_log_id'];
        }
    }

    else{
        echo 'failure';
    }
?>