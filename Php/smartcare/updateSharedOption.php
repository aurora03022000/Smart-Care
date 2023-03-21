<?php
if(isset($_POST['sharedOption'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $sharedOption = validate($_POST['sharedOption']);

    $sql = "UPDATE current_shared_patient SET shared_option='$sharedOption'";
    $result = $conn->query($sql);

    if($result){
        echo 'success';
    }

    else{
        echo 'failure';
    }

}
?>