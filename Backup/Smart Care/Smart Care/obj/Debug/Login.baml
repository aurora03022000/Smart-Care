   M S B A M L   `   `   `  ����E  ASmart Care, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:Smart_Care
Smart_Care  5   	       Smart_Care.Login   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   *localclr-namespace:Smart_Care     5   	     � Title$  Login��5   	   $��396.964��6   $	��800��6(    � WindowStartupLocation$ CenterScreen=�64    � 
ResizeMode$ NoResize=�6Y   .��5	      �  � ColumnDefinitions 5
   
   �� 5      $
��198* �6   6.   �� 5      $
��167* �6   6.   .+�5   
   � $��0��6   ���#0EB021.+�5      �� $��/backgroundLogin.jpg��6    J� Opacity$
 0.08Q�62   $
��Fill=�6A   � 5      .+�5      �� $��/applicationLogo.png��6   $
��Fill=�66   $��Center=�6E   $��Center=�6`   $	��157��6}   $	��169��6�   $��	0,-32,0,0q�6�   �� 5      $��
Smart Care��6   $��20(�6.   
�������$��Center=�6O   $��Center=�6j   $
��Bold"�6�   $��
12,118,0,0q�6�   $��Century Gothic)�6�   5      5      � 5   
   $��1��6   .+�5      �� $��/loginUser.png��6   $��UniformToFill=�6,   $��Center=�6D   $��Center=�6a   $��54��6|   $��55��6�   $��160,34,148.2,273.2q�6�   �� 5      $��Login to Smart Care��6   $��15(�63   $��Center=�6A   $��Center=�6\   $��
5,-162,0,0q�6y   $��Normal"�6�   $��Century Gothic)�6�   �� 5      $��Username��6   $��10(�6(   $��Center=�66   $��Center=�6Q   $��-153,-75,0,0q�6n   $��Century Gothic)�6�   �� 5      -   5         ��Name$ usernameTextBox��5      $��Center=�6/   $��	5,-26,0,0q�6L    �� TextWrapping$
 Wrap=�6_   $��Center=�6s   $	��207��6�   $��33��6�   $��5,8,0,0q�6�   4  .System.Windows.Automation.AutomationProperties   Name$ usernameTextBox��6�   $��Century Gothic)�6�   �� 5      $��Password��6   $��10(�6(   $��Center=�66   $��Center=�6Q   $��-159,30,0,0q�6n   $��Century Gothic)�6�   G� 5      -   5         G�Name$ passwordTextBox��5      $��Center=�63   $��5,79,0,0q�6P   $��Center=�6b   $	��206��6}   $��33��6�   $��5,8,0,0q�6�   $ passwordTextBox��6�   $��Century Gothic)�6�   �� 5      -   5        	 ��Name$	 loginButton��5   =   ���#0EB021
�������
 � Cursor$

 Hand��6R   $��Center=�6`   $��	5,192,0,0q�6}   $��11(�6�   $��Center=�6�   $	��113��6�   $��27��6�   $��Normal"�6�   $ loginButton��6�   $��Century Gothic)�6'   � 	Resources 5       �� 
TargetType �� Property �� Value��%^   '��       �� 5      " ��6   .�5       ��  �@��CornerRadius$ 5��69   5!      5"      .��5#      ]� .+�5$      �� $��key.png��6   $��21��6-   $��24��69   $��-7,0,5,0q�6D   �� 5%      $��Login��6    $��Center=�6-   5&      5'      �� 5(      $��Version 1.0��6   $��10(�6+   $��Bottom=�69   $��Center=�6T   $��10,0,0,4q�6q   $��Century Gothic)�6�   5)      5*      5+      