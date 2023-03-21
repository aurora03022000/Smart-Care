<?php
if(isset($_POST['currentUserID']) && isset($_POST['sharedPatientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUserID = validate($_POST['currentUserID']);
    $sharedPatientID = validate($_POST['sharedPatientID']);

    $sql = "SELECT * FROM patient WHERE patient_id='$sharedPatientID' and isExist=1";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){

            $share_id = $row['share_id'];

             if($share_id == "0" || $share_id == 0){
                $sql1 = "SELECT * FROM user WHERE user_id !='$currentUserID' and username != 'admin' and isExist=1 ORDER BY staff_name ASC";
                $result1 = mysqli_query($conn, $sql1);

                while($row1=mysqli_fetch_assoc($result1)){
                    echo $row1['staff_name']."`".$row1['user_id']."%";
                } 
             }

             else{
                $sql2 = "SELECT * FROM user WHERE user_id !='$currentUserID' and user_id!='$row[share_id]' and username != 'admin' and isExist=1 ORDER BY staff_name ASC";
                $result2 = mysqli_query($conn, $sql2);

                while($row2=mysqli_fetch_assoc($result2)){
                    echo $row2['staff_name']."`".$row2['user_id']."%";
                } 
             }
 
             
         } 
     }
 
     else{
         echo 'failure';
     }
    

}
?>