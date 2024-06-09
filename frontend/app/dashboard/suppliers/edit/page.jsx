'use client'
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';
import LoadingScreen from '@/components/Loading'

const EditPage = () => {
  const router = useRouter();
  useEffect(() => {
    router.push('/dashboard/suppliers')
  })
  return (
    <>
      <LoadingScreen></LoadingScreen>
    </>
  )
};

export default EditPage;