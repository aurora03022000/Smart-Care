<?php
if(isset($_POST['user_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $user_id = validate($_POST['user_id']);
    
    $sql = "UPDATE current_ids_admin SET current_user_clicked_id = '$user_id'";
    $result = $conn->query($sql);

        if($result){
            echo 'success';
        }

        else{
            echo 'failure';
        }
}
?>