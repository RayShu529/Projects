# Generated by Django 4.2.7 on 2023-12-03 07:17

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('adminPage', '0007_remove_lesson_example_lesson_exercise'),
    ]

    operations = [
        migrations.AlterField(
            model_name='lesson',
            name='exercise',
            field=models.TextField(blank=True, null=True, verbose_name='Lesson Exercise'),
        ),
    ]