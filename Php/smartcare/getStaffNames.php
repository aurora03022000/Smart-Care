<?php
if(isset($_POST['currentUserID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);

    $sql = "SELECT * FROM user WHERE user_id !='$currentUserID' and isExist=1 ORDER BY staff_name ASC";
    $result = mysqli_query($conn, $sql);
    
    if($result){
       while($row=mysqli_fetch_assoc($result)){
            echo $row['staff_name']."`";
        } 
    }

    else{
        echo 'failure';
    }
    

}
?>