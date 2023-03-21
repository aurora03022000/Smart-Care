<?php
if(isset($_POST['staffID']) && isset($_POST['currentUser'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $staffID = validate($_POST['staffID']);
    $currentUser = validate($_POST['currentUser']);

    $sql = "UPDATE patient SET share_id='$staffID' WHERE encoder='$currentUser' and isExist=1";
    $result = $conn->query($sql);
    
    if($result){
        echo 'success';
    }

    else{
        echo 'failure';
    }

}
?>