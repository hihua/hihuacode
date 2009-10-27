/*
 * CalendarManagerXML.cpp
 *
 *  Created on: 2009-10-13
 *      Author: Administrator
 */

#include "CalendarManagerXML.h"

CalendarManagerXML::CalendarManagerXML()
	{
	// TODO Auto-generated constructor stub
	g_SelectCalendarManagerInfo = NULL;
	g_FileName = NULL;
	}

CalendarManagerXML::~CalendarManagerXML()
	{
	// TODO Auto-generated destructor stub
	for (TInt i = 0;i < g_PointerArray_Key.Count();i++)
		{		
		SafeRelease(g_PointerArray_Key[i]);
		SafeRelease(g_PointerArray_Value[i]);		
		}
	
	g_XMLDocument.Clear();
	g_ScheduleMap.Close();
	g_PointerArray_Key.Close();
	g_PointerArray_Value.Close();	
	g_FileName = NULL;
	}

CalendarManagerXML* CalendarManagerXML::NewL(RFs& p_Fs, const TDes8& p_FilePath)
	{	
	CalendarManagerXML* self = CalendarManagerXML::NewLC(p_Fs, p_FilePath);
	CleanupStack::Pop(self);
	return self;
	}

CalendarManagerXML* CalendarManagerXML::NewLC(RFs& p_Fs, const TDes8& p_FilePath)
	{	
	CalendarManagerXML* self = new ( ELeave ) CalendarManagerXML();
	CleanupStack::PushL( self );
	self->ConstructL(p_Fs, p_FilePath);
	return self;
	}

void CalendarManagerXML::ConstructL(RFs& p_Fs, const TDes8& p_FilePath)
	{	
	g_FileName = (char *)(p_FilePath.Ptr());
//	HBufC* i_FilePath = CnvUtfConverter::ConvertToUnicodeFromUtf8L(p_FilePath);
//			
//	RFile file; 
//	TInt err = file.Open(p_Fs, i_FilePath->Des(), EFileWrite);
//	if (err != KErrNone)
//		{
//		err = file.Create(aFs, i_FilePath->Des(), EFileRead | EFileWrite); //如果打开文件错误，就创建文件！
//		}
		
//	err = file.Write(p_FilePath);
//	User::LeaveIfError(err); 
//	file.Close();	
	
//	
////	RFs aFs;
////	aFs.Connect();
////	CDir* dir = NULL;
////	aFs.GetDir(_L("Z:\\sys\\bin\\"), KEntryAttNormal, ESortNone, dir);
////	
////	TInt j = dir->Count();
////	for (TInt i = 0; i < j; i++)
////		{
////		HBufC* filename = (*dir)[i].iName.AllocL();				
////		}
//	//		
	
	if (g_XMLDocument.LoadFile(g_FileName, TIXML_ENCODING_UTF8))
		{
		TiXmlElement* i_RootElement = g_XMLDocument.RootElement();
		for (TiXmlElement* i_ParentElement = i_RootElement->FirstChildElement("Schedule_List"); i_ParentElement != NULL; i_ParentElement = i_ParentElement->NextSiblingElement())
			{			
			CalendarManagerSchedule* i_CalendarManagerSchedule = CalendarManagerSchedule::NewL();
												
			for (TiXmlElement* i_ContentElement = i_ParentElement->FirstChildElement("Schedule_Content"); i_ContentElement != NULL; i_ContentElement = i_ContentElement->NextSiblingElement())
				{
				const char* i_TimeElement = i_ContentElement->Attribute("Schedule_Time");
				const char* i_ReminderElement = i_ContentElement->Attribute("Schedule_Reminder");
				
				if (i_TimeElement != NULL && i_ReminderElement != NULL)
					{
					TPtrC8 i_TimePtr((const TUint8*)i_TimeElement, strlen(i_TimeElement));
					HBufC* i_Time = CnvUtfConverter::ConvertToUnicodeFromUtf8L(i_TimePtr);
					
					TPtrC8 i_ReminderPtr((const TUint8*)i_ReminderElement, strlen(i_ReminderElement));
					HBufC* i_Reminder = CnvUtfConverter::ConvertToUnicodeFromUtf8L(i_ReminderPtr);
					
					TPtrC8 i_ContentPtr((const TUint8*)i_ContentElement->GetText(), strlen(i_ContentElement->GetText()));
					HBufC* i_Content = CnvUtfConverter::ConvertToUnicodeFromUtf8L(i_ContentPtr);
					
					TLex i_Lex(i_Reminder->Des());
					TInt i_Num;
					i_Lex.Val(i_Num);
															
					SafeRelease(i_Reminder);
					
					CalendarManagerInfo* i_CalendarManagerInfo = CalendarManagerInfo::NewL();
					i_CalendarManagerInfo->g_Schedule_Reminder = i_Num;
					i_CalendarManagerInfo->g_Schedule_Content = i_Content;
					i_CalendarManagerInfo->g_XMLElement = i_ContentElement;
										
					i_CalendarManagerSchedule->g_ScheduleMap.InsertL(i_Time, i_CalendarManagerInfo);
					i_CalendarManagerSchedule->g_PointerArray_Key.AppendL(i_Time);
					i_CalendarManagerSchedule->g_PointerArray_Value.AppendL(i_CalendarManagerInfo);									
					
					for (TInt i = 0;i < i_Time->Des().Length();i++)
						{
						if (i_Time->Des().Mid(i, 1).Compare(_L(":")) == 0)
							{
							i_Time->Des().Replace(i, 1, _L("."));
							TLex i_Lex(i_Time->Des());
							TReal i_Real;
							i_Lex.Val(i_Real);														
							i_CalendarManagerSchedule->g_Array.AppendL(i_Real);							
							i_Time->Des().Replace(i, 1, _L(":"));
							}						
						}
					}			
				}
			
			TiXmlElement* i_DateElement = i_ParentElement->FirstChildElement("Schedule_Date");						
			if (i_DateElement != NULL && i_CalendarManagerSchedule->g_ScheduleMap.Count() > 0)
				{
				TPtrC8 i_DatePtr((const TUint8*)i_DateElement->GetText(), strlen(i_DateElement->GetText()));
				HBufC* i_Date = CnvUtfConverter::ConvertToUnicodeFromUtf8L(i_DatePtr);
								
				g_ScheduleMap.InsertL(i_Date, i_CalendarManagerSchedule);
				g_PointerArray_Key.AppendL(i_Date);
				g_PointerArray_Value.AppendL(i_CalendarManagerSchedule);
				}			
			}				
		}	
	}
