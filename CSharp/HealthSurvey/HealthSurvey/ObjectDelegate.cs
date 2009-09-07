using System;
using System.Collections.Generic;
using System.Runtime.CompilerServices;

namespace HealthSurvey
{
    public delegate void Form_ClientInfoClick_Handler(int ClientInfo_ID);
    public delegate void Form_ClientInfoDoubleClick_Handler(int ClientInfo_ID);    
    public delegate void Form_QuestionDoubleClick_Handler(int Question_ID, int ClientInfo_ID);
    public delegate void Form_QuestionClick_Handler(int Question_ID);

    public delegate void Form_ClientInfoRefresh_Handler();
    public delegate void Form_ClientListRefresh_Handler();
    public delegate void Form_ClientListSearch_Handler();
}