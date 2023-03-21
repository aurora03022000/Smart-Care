<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM current_shared_patient";
    $result = $conn->query($sql);

    if($result){
        while($row = mysqli_fetch_assoc($result)){
            echo $row['current_shared_id'];
        }
    }

    else{
        echo 'failure';
    }

?>