# Generated by Django 4.2.7 on 2023-12-02 11:52

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('loginRegister', '0006_user_certificate'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='datecertified',
            field=models.DateField(blank=True, null=True, verbose_name='Date of Certification'),
        ),
    ]