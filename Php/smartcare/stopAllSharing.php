<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);

    $sql = "UPDATE patient SET share_id='' WHERE encoder = '$currentUserID' and isExist=1";
    $result = mysqli_query($conn, $sql);
    
    if($result){
       echo 'success';
    }

    else{
        echo 'failure';
    }
    

}
?>