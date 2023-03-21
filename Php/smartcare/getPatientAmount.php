<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);

    $sql = "SELECT * FROM patient WHERE encoder='$currentUserID' and isExist=1";
    $result = $conn->query($sql);
    $rowsNumber = mysqli_num_rows($result);
    
    if($result){
        echo $rowsNumber;
    }

    else{
        echo 'failure';
    }

}
?>