package process;


public class Main {
	
	public static void main(String[] args) {
		
		String bssd = "201712";
		
		
		/*	
		 *	--------------- Job 14 ---------------
		 *	�����߸� �ó����� ����
		 * 	Source Table : 
		 *			EAS_IR_CURVE_HIS
		 *			EAS_SWAPTION_VOL
		 * 	Target Table : 
		 * 			EAS_RISK_NEUTRAL_SCE
		 * --------------------------------------
		 */
//		Job14_RiskNeutralScenario.run(bssd, false);
		
		
		
		/*	
		 * --------------- Job 15 ---------------
		 * ���Ǽ��� �ó����� ����
		 * Source Table : 
		 * 			EAS_IR_CURVE_HIS
		 * Target Table :
		 * 			EAS_REAL_WORLD_SCE
		 * --------------------------------------
		 */
//		Job15_RealWorldScenario.run(bssd, false);
		
		
		
		/*
		 * --------------- Job 21 ---------------
		 * Bottom-Up ������ ����
		 * Source Table :
		 * 			EAS_IR_CURVE_HIS
		 * 			EAS_DISC_RATE_CALC_SETTING
		 * 			EAS_DISC_RATE_HIS
		 * Target Table :
		 * 			EAS_DISC_RATE_STATS
		 * --------------------------------------
		 */
//		Job21_BottomUp.run(bssd);
		
		
		
		/*
		 * --------------- Job 31 ---------------
		 * �������� ��Ÿ ����
		 * Source Table :
		 * 			EAS_IR_CURVE_HIS
		 * Target Table :
		 * 			EAS_BOTTOMUP_DCNT
		 * --------------------------------------
		 */
//		Job31_DiscRateStatsInternal.run(bssd);
		
		
		
		/*
		 * --------------- Job 51 ---------------
		 * ��� �ε��� ����
		 * Source Table :
		 * 			EAS_USER_CORP_CRD_GRD_TM
		 * Target Table :
		 * 			EAS_CORP_CRD_GRD_CUM_PD
		 * --------------------------------------
		 */
		Job51_CorporatePd.run(bssd);
		
		
		/*
		 * --------------- Job 52 ---------------
		 * ���� �ε��� ����
		 * Source Table :
		 * 			EAS_USER_INDI_CRD_GRD_PD
		 * Target Table :
		 * 			EAS_INDI_CRD_GRD_CUM_PD
		 * --------------------------------------
		 */
		Job52_IndividualPd.run(bssd);
		
		
		
	}

}
