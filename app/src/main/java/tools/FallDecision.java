package tools;

import android.util.Log;
import android.widget.Toast;

public class FallDecision {
	public int count = 0;
	public int countpoint = 0;
	public int flag = 0;
	public int state = 1;
	public float sum = 0;
	float SuspectedFall[] = new float[100];
	/**
	 * new
	 */
	float jitter[] =new float [200];
	float sumjitter = 0;
	int jittercount = 0;

	public int Suspection1(float xvalue,float yvalue,float zvalue) {
		float AccelerationValue = xvalue * xvalue + yvalue * yvalue + zvalue
				* zvalue;
		if (state == 1) {
			if (AccelerationValue > 1300) {
				state = 2;
				sum = 0;
				count = 0;
				countpoint = 0;
				for(int i = 0;i < 100;i++){
					SuspectedFall[i]=0;					
				}
				//new
				jitter[jittercount]=AccelerationValue;
				jittercount = (jittercount + 1)%199 ;
				
				
				return 1;
			} else {
				//new
				jitter[jittercount]=AccelerationValue;
				jittercount = (jittercount + 1)%199 ;
				return 1;
			}
		} else {
			int answer = Suspection2(AccelerationValue);
			return answer;
		}
	}

	public int Suspection2(float AccelerationValue) {
		SuspectedFall[count] = AccelerationValue;
		sum += SuspectedFall[count];
		count = count + 1;
		//new
		jitter[jittercount]=AccelerationValue;
		jittercount = (jittercount + 1)%199 ;
//		//test
//		System.out.println("SuspectedFall[count] is "+SuspectedFall[count]);
		if (SuspectedFall[count] > 200) {
//			//test
//			System.out.println(countpoint);
			countpoint += 1;
		}
		if (SuspectedFall[count] > 1300) {
			state = 1;
			return 1;
		} else {
			if (sum > 15500 || countpoint > 5) {
				state = 1;
				return 1;
			} else {
				if (count == 99 ) {
	//				System.out.println(sum);
					for (int i = 0;i<200;i++){
						sumjitter = sumjitter + jitter[i];						
					}
					if (sumjitter > 26000){
						sumjitter = 0;
						state = 1;
						return 1;
					}
					else{
//						System.out.println(sumjitter);
						
						return 0;
					}

//					return 0;
				} else {
					state = 2;
					return 1;

				}
			}
		}

	}

}
