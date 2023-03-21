<?php
if(isset($_POST['hospital_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $hospital_id = validate($_POST['hospital_id']);

    $sql1 = "DELETE FROM hospital_list WHERE hospital_id='$hospital_id'";
    $result1 = $conn->query($sql1);

    if($result1){
        echo 'success';
    }

    else{
        echo 'failure';
    }
}
?>