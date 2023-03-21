<?php
    if(isset($_POST['patient_id'])){

        require_once "conn.php";
        require_once "validate.php";
        
        $patient_id = validate($_POST['patient_id']);

    $sql = "SELECT * FROM patient WHERE patient_id='$patient_id'";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM room";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row2=mysqli_fetch_assoc($result1)){
                    $sql2= "SELECT * FROM room ORDER BY room_number ASC";
                    $result2 = mysqli_query($conn, $sql2);

                    if($result2){
                        while($row3=mysqli_fetch_assoc($result2)){
                            echo $row3['room_number'].'`';
                        }
                    }

                    else{
                        echo 'failure'; 
                    }
                }
            }

            else{
                echo 'failure';
            }
        } 
    }

    else{
        echo 'failure';
    }

    
}  
?>