from django.contrib import admin
from . import models
# Register your models here.
admin.site.register(models.QuizScore)
admin.site.register(models.AssessmentScore)
admin.site.register(models.ActivityLog)