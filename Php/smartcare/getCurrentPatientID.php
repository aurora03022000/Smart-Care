<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM current_patient";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){
            echo $row['current_patient_id'];
        }
    }

    else{
        echo 'failure';
    }

?>