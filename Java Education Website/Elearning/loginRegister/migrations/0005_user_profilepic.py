# Generated by Django 4.2.7 on 2023-11-24 12:36

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('loginRegister', '0004_rename_student_user'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='profilepic',
            field=models.ImageField(blank=True, null=True, upload_to='images/', verbose_name='profilepic'),
        ),
    ]
