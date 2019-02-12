#-Begin-----------------------------------------------------------------

#-Includes--------------------------------------------------------------
import sys, win32com.client

#-Sub Main--------------------------------------------------------------
def Main(arg1, arg2, arg3):
	try:
		#print("try")
		SapGuiAuto = win32com.client.GetObject("SAPGUI")
		if not type(SapGuiAuto) == win32com.client.CDispatch:
			return
		#print("1")
		application = SapGuiAuto.GetScriptingEngine
		if not type(application) == win32com.client.CDispatch:
			SapGuiAuto = None
			return
		#print("2")
		connection = application.Children(0)
		if not type(connection) == win32com.client.CDispatch:
			application = None
			SapGuiAuto = None
			return
		#print("3")
		session = connection.Children(0)
		#print("5")
		if not type(session) == win32com.client.CDispatch:
			connection = None
			application = None
			SapGuiAuto = None
			return
		#print("4")
		session.findById("wnd[0]").resizeWorkingPane(133,24,"false")
		#print("script: 1")
		session.findById("wnd[0]/tbar[0]/okcd").text = "sa38"
		#print("script: 2")
		session.findById("wnd[0]").sendVKey(0)
		#print("script: 3")
		session.findById("wnd[0]/usr/ctxtRS38M-PROGRAMM").text = "z5rfid20"
		#print("script: 4")
		session.findById("wnd[0]").sendVKey(8)
		#print("script: 5")
		session.findById("wnd[0]/usr/ctxtSO_AUFNR-LOW").text = arg1
		#print("script: 6")
		session.findById("wnd[0]").sendVKey(8)
		#print("script: 7")
		session.findById("wnd[0]").sendVKey(45)
		#print("script: 8")
		session.findById("wnd[1]").sendVKey(0)
		#print("script: 9")
		session.findById("wnd[1]/usr/ctxtDY_PATH").text = arg2
		#print("script: 10")
		session.findById("wnd[1]/usr/ctxtDY_FILENAME").text = arg3
		#print("script: 11")
		session.findById("wnd[1]").sendVKey(11)
		#print("script: 12")
		session.findById("wnd[0]").sendVKey(12)
		#print("script: 13")
		session.findById("wnd[0]").sendVKey(12)
		#print("script: 14")
		session.findById("wnd[0]").sendVKey(12)
		#print("script: 15")
	except:
		print(sys.exc_info()[0])
	finally:
		session = None
		connection = None
		application = None
		SapGuiAuto = None

#-Main------------------------------------------------------------------
if __name__ == "__main__":
	Main(sys.argv[1], sys.argv[2], sys.argv[3])
		
#-End-------------------------------------------------------------------