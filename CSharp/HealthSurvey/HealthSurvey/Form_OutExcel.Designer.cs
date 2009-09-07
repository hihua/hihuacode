namespace HealthSurvey
{
    partial class Form_OutExcel
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.listView1 = new System.Windows.Forms.ListView();
            this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader2 = new System.Windows.Forms.ColumnHeader();
            this.choiButton1 = new ChoiControls.ChoiButton();
            this.choiButton2 = new ChoiControls.ChoiButton();
            this.columnHeader3 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader4 = new System.Windows.Forms.ColumnHeader();
            this.columnHeader5 = new System.Windows.Forms.ColumnHeader();
            this.SuspendLayout();
            // 
            // listView1
            // 
            this.listView1.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2,
            this.columnHeader3,
            this.columnHeader4,
            this.columnHeader5});
            this.listView1.Dock = System.Windows.Forms.DockStyle.Top;
            this.listView1.FullRowSelect = true;
            this.listView1.GridLines = true;
            this.listView1.HideSelection = false;
            this.listView1.Location = new System.Drawing.Point(0, 0);
            this.listView1.Name = "listView1";
            this.listView1.Size = new System.Drawing.Size(410, 435);
            this.listView1.TabIndex = 1;
            this.listView1.UseCompatibleStateImageBehavior = false;
            this.listView1.View = System.Windows.Forms.View.Details;
            // 
            // columnHeader1
            // 
            this.columnHeader1.Text = "编号";
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "姓名";
            this.columnHeader2.Width = 109;
            // 
            // choiButton1
            // 
            this.choiButton1.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.choiButton1.Arrow = ChoiControls.ChoiButton.enum_Arrow.ToRight;
            this.choiButton1.ArrowRadius = 8;
            this.choiButton1.BackColor = System.Drawing.Color.Transparent;
            this.choiButton1.ColorBase = System.Drawing.Color.FromArgb(((int)(((byte)(195)))), ((int)(((byte)(215)))), ((int)(((byte)(225)))));
            this.choiButton1.ColorBaseStroke = System.Drawing.Color.SteelBlue;
            this.choiButton1.ColorOn = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(214)))), ((int)(((byte)(78)))));
            this.choiButton1.ColorOnStroke = System.Drawing.Color.FromArgb(((int)(((byte)(196)))), ((int)(((byte)(177)))), ((int)(((byte)(118)))));
            this.choiButton1.ColorPress = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(128)))));
            this.choiButton1.ColorPressStroke = System.Drawing.Color.Goldenrod;
            this.choiButton1.Distance = 2;
            this.choiButton1.FadingSpeed = 35;
            this.choiButton1.FlatAppearance.BorderSize = 0;
            this.choiButton1.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.choiButton1.GroupPos = ChoiControls.ChoiButton.enum_GroupPos.Center;
            this.choiButton1.ImageLocation = ChoiControls.ChoiButton.enum_ImageLocation.Left;
            this.choiButton1.IsPressed = false;
            this.choiButton1.KeepPress = false;
            this.choiButton1.Location = new System.Drawing.Point(68, 458);
            this.choiButton1.MenuPos = new System.Drawing.Point(0, 0);
            this.choiButton1.Name = "choiButton1";
            this.choiButton1.Radius = 6;
            this.choiButton1.ShowBaseColor = ChoiControls.ChoiButton.enum_ShowBaseColor.Yes;
            this.choiButton1.Size = new System.Drawing.Size(75, 27);
            this.choiButton1.SplitButton = ChoiControls.ChoiButton.enum_SplitButton.No;
            this.choiButton1.SplitDistance = 16;
            this.choiButton1.TabIndex = 3;
            this.choiButton1.Text = "选定导出";
            this.choiButton1.UseVisualStyleBackColor = true;
            this.choiButton1.Click += new System.EventHandler(this.choiButton1_Click);
            // 
            // choiButton2
            // 
            this.choiButton2.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.choiButton2.Arrow = ChoiControls.ChoiButton.enum_Arrow.ToRight;
            this.choiButton2.ArrowRadius = 8;
            this.choiButton2.BackColor = System.Drawing.Color.Transparent;
            this.choiButton2.ColorBase = System.Drawing.Color.FromArgb(((int)(((byte)(195)))), ((int)(((byte)(215)))), ((int)(((byte)(225)))));
            this.choiButton2.ColorBaseStroke = System.Drawing.Color.SteelBlue;
            this.choiButton2.ColorOn = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(214)))), ((int)(((byte)(78)))));
            this.choiButton2.ColorOnStroke = System.Drawing.Color.FromArgb(((int)(((byte)(196)))), ((int)(((byte)(177)))), ((int)(((byte)(118)))));
            this.choiButton2.ColorPress = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(192)))), ((int)(((byte)(128)))));
            this.choiButton2.ColorPressStroke = System.Drawing.Color.Goldenrod;
            this.choiButton2.Distance = 2;
            this.choiButton2.FadingSpeed = 35;
            this.choiButton2.FlatAppearance.BorderSize = 0;
            this.choiButton2.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.choiButton2.GroupPos = ChoiControls.ChoiButton.enum_GroupPos.Center;
            this.choiButton2.ImageLocation = ChoiControls.ChoiButton.enum_ImageLocation.Left;
            this.choiButton2.IsPressed = false;
            this.choiButton2.KeepPress = false;
            this.choiButton2.Location = new System.Drawing.Point(261, 458);
            this.choiButton2.MenuPos = new System.Drawing.Point(0, 0);
            this.choiButton2.Name = "choiButton2";
            this.choiButton2.Radius = 6;
            this.choiButton2.ShowBaseColor = ChoiControls.ChoiButton.enum_ShowBaseColor.Yes;
            this.choiButton2.Size = new System.Drawing.Size(75, 27);
            this.choiButton2.SplitButton = ChoiControls.ChoiButton.enum_SplitButton.No;
            this.choiButton2.SplitDistance = 16;
            this.choiButton2.TabIndex = 4;
            this.choiButton2.Text = "导出全部";
            this.choiButton2.UseVisualStyleBackColor = true;
            this.choiButton2.Click += new System.EventHandler(this.choiButton2_Click);
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "年龄";
            // 
            // columnHeader4
            // 
            this.columnHeader4.Text = "性别";
            // 
            // columnHeader5
            // 
            this.columnHeader5.Text = "题目数";
            this.columnHeader5.Width = 80;
            // 
            // Form_OutExcel
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(410, 497);
            this.Controls.Add(this.choiButton2);
            this.Controls.Add(this.choiButton1);
            this.Controls.Add(this.listView1);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Form_OutExcel";
            this.Text = "Form_OutExcel";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListView listView1;
        private System.Windows.Forms.ColumnHeader columnHeader1;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private ChoiControls.ChoiButton choiButton1;
        private ChoiControls.ChoiButton choiButton2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.ColumnHeader columnHeader4;
        private System.Windows.Forms.ColumnHeader columnHeader5;
    }
}