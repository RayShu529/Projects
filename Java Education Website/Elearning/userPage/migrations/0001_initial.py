# Generated by Django 4.2.7 on 2023-12-01 11:30

from django.db import migrations, models
import django.utils.timezone


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='ActivityLog',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('username', models.CharField(default='', max_length=50, verbose_name='Username')),
                ('action', models.CharField(default='', max_length=50, verbose_name='Action')),
                ('date', models.DateTimeField(default=django.utils.timezone.now, verbose_name='Date')),
            ],
        ),
        migrations.CreateModel(
            name='AssessmentScore',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('username', models.CharField(default='', max_length=50, verbose_name='Username')),
                ('lessonName', models.CharField(default='', max_length=50, verbose_name='Lesson Name')),
                ('score', models.IntegerField(default=0, verbose_name='Score')),
                ('date', models.DateTimeField(default=django.utils.timezone.now, verbose_name='Date')),
            ],
        ),
        migrations.CreateModel(
            name='QuizScore',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('username', models.CharField(default='', max_length=50, verbose_name='Username')),
                ('lessonName', models.CharField(default='', max_length=50, verbose_name='Lesson Name')),
                ('score', models.IntegerField(default=0, verbose_name='Score')),
                ('date', models.DateTimeField(default=django.utils.timezone.now, verbose_name='Date')),
            ],
        ),
    ]
