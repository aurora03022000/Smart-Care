<?php
if(isset($_POST['id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $id = validate($_POST['id']);

    $sql = "UPDATE current_log_open SET log_id='$id'";
    $result = $conn->query($sql);

    if($result){
        echo 'success';
    }

    else{
        echo 'failure';
    }

}
?>