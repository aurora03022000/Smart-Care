   M S B A M L   `   `   `  ����E  ASmart Care, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:Smart_Care
Smart_Care  5   	       Smart_Care.RegisterStaff   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   *localclr-namespace:Smart_Care     5   	     � Title$  Register Staff��5   	   $��522.592��6    $��426.805��61    � 
ResizeMode$ NoResize=�6A    � WindowStartupLocation$ CenterScreen=�6W   .��5	      � .+�5
   
   � $��
12,-20,0,0q�6   $��Center=�6#   $��Center=�6>   .+�5      �� $��	staff.png��6   $��Center=�6'   $��Center=�6D   $��44��6_   $��-206,-392,0,0q�6k   $��46��6�   $
��Fill=�6�   �� 5      $��Register Medical Staff��6   $��51,-385,0,0q�65   $��Center=�6J   $��20(�6e   $��Century Gothic)�6s   $��Center=�6�   �� 5      $��Account Credentials��6   $��Italic$�62   $��Century Gothic)�6E   $��10(�6a   $��Center=�6o   $��Center=�6�   $��-212,-273,0,0q�6�   � 5      
�������$��1��6)   $��Center=�64   $	��320��6O   $��
3,-253,0,0q�6[   �� 5      $��
Username :��6   $��12(�6)   $��Center=�67   $��-257,-210,0,0q�6T   $��Center=�6k   $��Century Gothic)�6�   $��68��6�   $��28��6�   $
��Bold"�6�   �� 5      $��
Password :��6   $��Center=�6)   $��-262,-145,0,0q�6F   $��Center=�6]   $��Century Gothic)�6x   $
��Bold"�6�   �� 5      $��Name :��6   $��Center=�6%   $��-277,21,0,0q�6B   $��Center=�6W   $��Century Gothic)�6r   $
��Bold"�6�   �� 5      $��Birthdate :��6   $��Center=�6*   $��-266,157,0,0q�6G   $��Center=�6]   $��Century Gothic)�6x   $
��Bold"�6�   �� 5      $��Gender :��6   $��Center=�6'   $��-271,87,0,0q�6D   $��Center=�6Y   $��Century Gothic)�6t   $
��Bold"�6�   �� 5      $��	Address :��6   $��Center=�6(   $��-275,225,0,0q�6E   $��Center=�6[   $��Century Gothic)�6v   $
��Bold"�6�   �� 5      $��Number :��6   $��Center=�6'   $��-272,291,0,0q�6D   $��Center=�6Z   $��Century Gothic)�6u   $
��Bold"�6�   �� 5      -   5         ��Name$ username��5      $��Center=�6(   $��28��6E   $��63,-209,0,0q�6Q    �� TextWrapping$
 Wrap=�6f   $��Center=�6z   $	��250��6�   $��Century Gothic)�6�   $��3,5,0,0q�6�   G� 5      -   5         G�Name$ password��5   6   $��Century Gothic)�6   $��Center=�6H   $��62,-143,0,0q�6e   $��Center=�6z   $	��250��6�   $��28��6�   $��3,5,0,0q�6�   �� 5      -   5        $
 name��5      $��Center=�6$   $��28��6A   $��	63,22,0,0q�6M   $
 Wrap=�6`   $��Center=�6t   $	��250��6�   $��Century Gothic)�6�   $��3,5,0,0q�6�   �� 5      -   5         ��Name$ gender��5   A    � Cursor$
 Hand��6   $��Century Gothic)�6%   $��Center=�6Q   $��Center=�6n   $��	63,88,0,0q�6�   $��28��6�   $	��250��6�   $��8,8,0,0q�6�   .@�5      �� .��6   Male6%   �� 5      .��6   Female6'   5      (  "System.Windows.Controls.DatePicker  5      -   5          Name$ bdate��5   5   $��Century Gothic)�6   $��Center=�6D   $��
62,157,0,0q�6a   $��Center=�6u   $��28��6�   $	��250��6�   $��3,5,0,0q�6�   �� 5      -   5        $ address��5   2   $��Century Gothic)�6   $��Center=�6C   $��28��6`   $��
63,225,0,0q�6l   $
 Wrap=�6�   $��Center=�6�   $	��250��6�   $��3,5,0,0q�6�   �� 5      -   5        $ number��5   [   $��Century Gothic)�6   $��Center=�6k   $��28��6�   $��
62,292,0,0q�6�   $
 Wrap=�6�   $��Center=�6�   $	��250��6�   $��3,5,0,0q�6�   �� 5       -   5        	 ��Name$	 RegisterStaff_Button��5    g   $
 Hand��6   ���#0EB021
�������$��Century Gothic)�6K   $��Center=�6�   $��
41,417,0,0q�6�   $��Center=�6�   $	��173��6�   $��32��6   
 � 	Resources
 5!       �� 
TargetType �� Property �� Value��%^   '��       �� 5"      " ��6   .�5#      ��  �@��CornerRadius$ 7��69   5$      5%      .��5&      ]� .+�5'      �� $��	staff.png��6   $��21��6/   $��24��6;   $��0,0,2,0q�6F   �� 5(      $��Register Staff ��6    $��Center=�67   5)      5*      �� 5+      $��Account Information��6   $��Italic$�62   $��Century Gothic)�6E   $��10(�6a   $��Center=�6o   $��Center=�6�   $��-213,-41,0,0q�6�   � 5,      
�������$��1��6)   $��Center=�64   $	��320��6O   $��
-1,-22,0,0q�6[   5-      5/      50      