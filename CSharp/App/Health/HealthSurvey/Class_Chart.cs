using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.Drawing.Design;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging;
using System.IO;
using System.Text;

using System.Windows.Forms.DataVisualization.Charting;

namespace HealthSurvey
{
    public class Class_Chart
    {
        private DataTable dataTable = null;
        private String dataX = "";
        private String dataY = "";
        private Color dataColor = Color.Blue;
        private int width = 0;
        private int height = 0;

        public Class_Chart(DataTable Data, String DataX, String DataY, Color DataColor, int Width, int Height) 
        {
            dataTable = Data;
            dataX = DataX;
            dataY = DataY;
            dataColor = DataColor;
            width = Width;
            height = Height;
        }

        private bool Check_Chart()
        {
            if (dataTable == null || dataTable.Rows.Count <= 0)
                return false;

            if (String.IsNullOrEmpty(dataX) || String.IsNullOrEmpty(dataY))
                return false;

            if (dataColor == null)
                return false;

            if (width <= 0 || height <= 0)
                return false;

            return true;
        }
        
        public Bitmap Show_MSChart()
        {
            try
            {
                if (!Check_Chart())
                    return null;

                ChartArea chartArea = new ChartArea("chartArea");
                Grid grid = new Grid();
                grid.LineDashStyle = ChartDashStyle.Solid;
                grid.LineColor = Color.Black;

                Legend lengend = new Legend();
                lengend.Docking = Docking.Right;
                                                
                chartArea.AxisX.MajorGrid = grid;
                chartArea.AxisY.MajorGrid = grid;
                chartArea.AxisX.Interval = 1;
                chartArea.AxisX.IsLabelAutoFit = false;
                chartArea.BackColor = Color.FromArgb(0xEF, 0xEF, 0xEF);

                Series series = new Series("危险度");
                series.ChartType = SeriesChartType.Column;
                //series.IsValueShownAsLabel = true;
                series.Color = dataColor;
                series.BorderWidth = 0;                
                                                
                SmartLabelStyle smartLabelStyle = new SmartLabelStyle();
                smartLabelStyle.AllowOutsidePlotArea = LabelOutsidePlotAreaStyle.Yes;
                series.SmartLabelStyle = smartLabelStyle;

                series.Points.DataBindXY(dataTable.DefaultView, dataX, dataTable.DefaultView, dataY);
                Chart chart = new Chart();
                chart.Width = width;
                chart.Height = height;
                chart.ChartAreas.Add(chartArea);
                chart.Series.Add(series);
                chart.Legends.Add(lengend);

                MemoryStream memoryStream = new MemoryStream();
                chart.SaveImage(memoryStream, ChartImageFormat.Jpeg);

                Bitmap bitmap = new Bitmap(memoryStream);                
                return bitmap;
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}
